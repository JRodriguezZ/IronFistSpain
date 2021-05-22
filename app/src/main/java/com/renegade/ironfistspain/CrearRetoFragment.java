package com.renegade.ironfistspain;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.dpro.widgets.WeekdaysPicker;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.renegade.ironfistspain.databinding.FragmentCrearRetoBinding;
import com.renegade.ironfistspain.databinding.ViewholderSeleccionRivalBinding;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CrearRetoFragment extends BaseFragment {
    Date dateMin, dateMax;
    SimpleDateFormat f24horas;
    private FragmentCrearRetoBinding binding;

    List<Integer> diasSeleccionados = Arrays.asList();
    List<Rival> rivalesDisponibles = new ArrayList<>();

    RivalesAdapter rivalesAdapter = new RivalesAdapter();

    List<Rival> rivalSeleccionado = new ArrayList<>();

    class Rival { String uid; String nombre; Long puntuacion; String imagen; Boolean estaSeleccionado; public Rival(String uid, String nombre, Long puntuacion, String imagen) { this.uid = uid; this.nombre = nombre; this.puntuacion = puntuacion; this.imagen = imagen; estaSeleccionado = false;}}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentCrearRetoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewRivales.setLayoutManager(horizontalLayoutManager);
//        binding.recyclerViewRivales.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));
        binding.recyclerViewRivales.setAdapter(rivalesAdapter);

        db.collection(CollectionDB.USUARIOS).document(user.getUid()).get().addOnSuccessListener(doc -> {
//            int p;
//            String s = null;
//            try {

            Long p = doc.getLong("puntuacion"); // 1000

            String s = doc.getString("nickname");

            Log.e("ABCD", "Puntuacion del usuario: " + p + " Nombre del usuario: " + s);
//            } catch (Exception e){
//                System.out.println("PUNTUACION INVALIDA EN LA BASE DE DATOS");
//                p = 1000;
//            }

            db.collection(CollectionDB.USUARIOS)
                    .whereGreaterThanOrEqualTo("puntuacion", (p-200))
                    .whereLessThanOrEqualTo("puntuacion", (p+200))
//                    .orderBy("puntuacion", Query.Direction.valueOf("asc"))
                    .addSnapshotListener((value, error) -> {
                        rivalesDisponibles.clear();
                        for (QueryDocumentSnapshot rival : value) {
                            if (!Objects.equals(rival.getString("nickname"), s)) {
                                String uid = rival.getString("uid");
                                String nickname = rival.getString("nickname");
                                Long puntuacion = rival.getLong("puntuacion");
                                String imagen = rival.getString("imagen");

                                Log.e("ABCD", "Consulta: " + s + " = " + nickname + ", " + puntuacion);

                                rivalesDisponibles.add(new Rival(uid, nickname, puntuacion, imagen));
                            }

                            rivalesAdapter.notifyDataSetChanged();

                            if (rivalesDisponibles.size() == 0) binding.noHayRivalesDisp.setVisibility(View.VISIBLE);
                            else binding.noHayRivalesDisp.setVisibility(View.GONE);
                        }
            });
        });

        WeekdaysPicker weekdaysPicker = binding.weekdays;
        weekdaysPicker.setSelectedDays(diasSeleccionados);
        weekdaysPicker.setOnWeekdaysChangeListener((view1, clickedDayOfWeek, selectedDays) -> {
                diasSeleccionados = weekdaysPicker.getSelectedDays();

            Log.e("ABCD", "Dias seleccionados: " + diasSeleccionados);
        });

        binding.botonHora1.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    R.style.Theme_Dialog,
                    (timePicker, hourOfDay, minute) -> {
                        String tiempo = hourOfDay + ":" + minute;
                        f24horas = new SimpleDateFormat("HH:mm");
                        try {
                            dateMin = f24horas.parse(tiempo);
                            binding.botonHora1.setText(f24horas.format(dateMin));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }, 12, 0 , true);
            timePickerDialog.show();
        });

        binding.botonHora2.setOnClickListener(view1 -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    R.style.Theme_Dialog,
                    (view2, hourOfDay, minute) -> {
                        String tiempo = hourOfDay + ":" + minute;
                        f24horas = new SimpleDateFormat("HH:mm");
                        try {
                            dateMax = f24horas.parse(tiempo);
                            binding.botonHora2.setText(f24horas.format(dateMax));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    },12,0,true
            );
            timePickerDialog.show();
        });

        binding.enviarRetoButton.setOnClickListener(v -> {
            // Estados del encuentro: Enviado -> Aceptado/Cancelado -> En proceso (-> Planeado) -> Completado

            if (rivalSeleccionado.size() == 0 || diasSeleccionados.size() == 0 || dateMin == null || dateMax == null ) {
                Toast.makeText(getActivity(),"Hay campos sin rellenar!",Toast.LENGTH_LONG).show();
            } else {
                db.collection("Encuentros")
                        .document()
                        .set(new Encuentro("Enviado", user.getUid(), rivalSeleccionado.get(0).uid, diasSeleccionados, f24horas.format(dateMin), f24horas.format(dateMax)));
                Toast.makeText(getActivity(), "¡Se ha enviado el reto correctamente!", Toast.LENGTH_SHORT).show();
                nav.navigate(R.id.action_crearRetoFragment_to_retosPendientesFragment2);
            }
        });

    }

    class RivalesAdapter extends RecyclerView.Adapter<RivalViewHolder> {

        int posicionOriginal = -1;

        Rival rivalAnterior;

        @NonNull
        @Override
        public RivalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RivalViewHolder(ViewholderSeleccionRivalBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CrearRetoFragment.RivalViewHolder holder, int position) {
            Rival rival = rivalesDisponibles.get(position);

            holder.binding.nombreRival.setText(rival.nombre);
            Glide.with(requireContext()).load(rival.imagen).circleCrop().into(holder.binding.imagenRival);
            holder.binding.puntuacionRival.setText(""+rival.puntuacion);

            holder.itemView.setOnClickListener(v -> {

                binding.nombreRivalSeleccionado.setTextColor(Color.rgb(139,0,0));

//                viewModel.nombreRivalLiveData.setValue(rival.nombre);   // aqui poner el ID del personaje
//                viewModel.imagenRivalLiveData.setValue(rival.imagen);
//                viewModel.puntuacionRangoLiveData.setValue(String.valueOf(rival.puntuacion));

//                for (Rival rival1: rivalesDisponibles) {
//                    rival1.estaSeleccionado = false;
//                    if (rival1.nombre.equals(rivalesDisponibles.get(position).nombre)) {
//                        Log.e("ABCD", "El rival " + rival1.nombre + " esta en la misma posicion que " + rivalesDisponibles.get(position).nombre);
//                        rival1.estaSeleccionado = true;
//                        Glide.with(requireContext())
//                                .load(rival1.imagen)
//                                .circleCrop()
//                                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(getContext(), 1000,2, "#8B0000",5)))
//                                .into(holder.binding.imagenRival);
//                    } else {
//                        rival1.estaSeleccionado = false;
//                        Log.e("ABCD", "El rival " + rival1.nombre + " NO esta en la misma posicion que " + rivalesDisponibles.get(position).nombre);
//                        Glide.with(requireContext())
//                                .load(rival1.imagen)
//                                .circleCrop()
////                                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(getContext(), 1000,2, "#8B0000",5)))
//                                .into(holder.binding.imagenRival);
//                    }
//                }

                rivalSeleccionado.clear();
                rivalSeleccionado.add(new Rival(rival.uid, rival.nombre, rival.puntuacion, rival.imagen));
                Log.e("ABCD", rivalSeleccionado.get(0).uid);

                binding.nombreRivalSeleccionado.setText(rival.nombre);
                binding.puntuacionRivalSeleccionado.setText(String.valueOf(rival.puntuacion));
                posicionOriginal = position;
            });
        }

        @Override
        public int getItemCount() {
            return rivalesDisponibles == null ? 10 : rivalesDisponibles.size();
        }
    }

    static class RivalViewHolder extends RecyclerView.ViewHolder {
        ViewholderSeleccionRivalBinding binding;
        public RivalViewHolder(@NonNull ViewholderSeleccionRivalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        String tiempo = hourOfDay + ":" + minute;
        try {
            devolverHoraEscogida(tiempo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String devolverHoraEscogida(String time) throws ParseException {
        SimpleDateFormat f24horas = new SimpleDateFormat("HH:mm");
        Date date = f24horas.parse(time);
        return f24horas.format(date);
    }
}
class RoundedCornersTransformation implements Transformation<Bitmap> {

    public enum CornerType {
        ALL,
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
        TOP, BOTTOM, LEFT, RIGHT,
        OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
        DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT, BORDER
    }

    private BitmapPool mBitmapPool;
    private int mRadius;
    private int mDiameter;
    private int mMargin;
    private CornerType mCornerType;
    private String mColor;
    private int mBorder;

    public RoundedCornersTransformation(Context context, int radius, int margin) {
        this(context, radius, margin, CornerType.ALL);
    }

    public RoundedCornersTransformation(Context context, int radius, int margin, String color, int border) {
        this(context, radius, margin, CornerType.BORDER);
        mColor = color;
        mBorder = border;
    }

    public RoundedCornersTransformation(BitmapPool pool, int radius, int margin) {
        this(pool, radius, margin, CornerType.ALL);
    }

    public RoundedCornersTransformation(Context context, int radius, int margin,
                                        CornerType cornerType) {
        this(Glide.get(context).getBitmapPool(), radius, margin, cornerType);
    }

    public RoundedCornersTransformation(BitmapPool pool, int radius, int margin,
                                        CornerType cornerType) {
        mBitmapPool = pool;
        mRadius = radius;
        mDiameter = mRadius * 2;
        mMargin = margin;
        mCornerType = cornerType;
    }

    @Override
    public Resource<Bitmap> transform(Context context, Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        drawRoundRect(canvas, paint, width, height);
        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    private void drawRoundRect(Canvas canvas, Paint paint, float width, float height) {
        float right = width - mMargin;
        float bottom = height - mMargin;

        switch (mCornerType) {
            case ALL:
                canvas.drawRoundRect(new RectF(mMargin, mMargin, right, bottom), mRadius, mRadius, paint);
                break;
            case TOP_LEFT:
                drawTopLeftRoundRect(canvas, paint, right, bottom);
                break;
            case TOP_RIGHT:
                drawTopRightRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM_LEFT:
                drawBottomLeftRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM_RIGHT:
                drawBottomRightRoundRect(canvas, paint, right, bottom);
                break;
            case TOP:
                drawTopRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM:
                drawBottomRoundRect(canvas, paint, right, bottom);
                break;
            case LEFT:
                drawLeftRoundRect(canvas, paint, right, bottom);
                break;
            case RIGHT:
                drawRightRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_TOP_LEFT:
                drawOtherTopLeftRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_TOP_RIGHT:
                drawOtherTopRightRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_BOTTOM_LEFT:
                drawOtherBottomLeftRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_BOTTOM_RIGHT:
                drawOtherBottomRightRoundRect(canvas, paint, right, bottom);
                break;
            case DIAGONAL_FROM_TOP_LEFT:
                drawDiagonalFromTopLeftRoundRect(canvas, paint, right, bottom);
                break;
            case DIAGONAL_FROM_TOP_RIGHT:
                drawDiagonalFromTopRightRoundRect(canvas, paint, right, bottom);
                break;
            case BORDER:
                drawBorder(canvas, paint, right, bottom);
                break;
            default:
                canvas.drawRoundRect(new RectF(mMargin, mMargin, right, bottom), mRadius, mRadius, paint);
                break;
        }
    }

    private void drawTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, mMargin + mDiameter),
                mRadius, mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin + mRadius, mMargin + mRadius, bottom), paint);
        canvas.drawRect(new RectF(mMargin + mRadius, mMargin, right, bottom), paint);
    }

    private void drawTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, mMargin + mDiameter), mRadius,
                mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom), paint);
        canvas.drawRect(new RectF(right - mRadius, mMargin + mRadius, right, bottom), paint);
    }

    private void drawBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, mMargin + mDiameter, bottom),
                mRadius, mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin, mMargin + mDiameter, bottom - mRadius), paint);
        canvas.drawRect(new RectF(mMargin + mRadius, mMargin, right, bottom), paint);
    }

    private void drawBottomRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius,
                mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom), paint);
        canvas.drawRect(new RectF(right - mRadius, mMargin, right, bottom - mRadius), paint);
    }

    private void drawTopRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin, mMargin + mRadius, right, bottom), paint);
    }

    private void drawBottomRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right, bottom - mRadius), paint);
    }

    private void drawLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, bottom), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin + mRadius, mMargin, right, bottom), paint);
    }

    private void drawRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, bottom), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom), paint);
    }

    private void drawOtherTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
                paint);
        canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, bottom), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom - mRadius), paint);
    }

    private void drawOtherTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, bottom), mRadius, mRadius,
                paint);
        canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin + mRadius, mMargin, right, bottom - mRadius), paint);
    }

    private void drawOtherBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
                paint);
        canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, bottom), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin, mMargin + mRadius, right - mRadius, bottom), paint);
    }

    private void drawOtherBottomRightRoundRect(Canvas canvas, Paint paint, float right,
                                               float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
                paint);
        canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, bottom), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin + mRadius, mMargin + mRadius, right, bottom), paint);
    }

    private void drawDiagonalFromTopLeftRoundRect(Canvas canvas, Paint paint, float right,
                                                  float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, mMargin + mDiameter),
                mRadius, mRadius, paint);
        canvas.drawRoundRect(new RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius,
                mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin + mRadius, right - mDiameter, bottom), paint);
        canvas.drawRect(new RectF(mMargin + mDiameter, mMargin, right, bottom - mRadius), paint);
    }

    private void drawDiagonalFromTopRightRoundRect(Canvas canvas, Paint paint, float right,
                                                   float bottom) {
        canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, mMargin + mDiameter), mRadius,
                mRadius, paint);
        canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, mMargin + mDiameter, bottom),
                mRadius, mRadius, paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom - mRadius), paint);
        canvas.drawRect(new RectF(mMargin + mRadius, mMargin + mRadius, right, bottom), paint);
    }

    private void drawBorder(Canvas canvas, Paint paint, float right,
                            float bottom) {

        // stroke
        Paint strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        if (mColor != null) {
            strokePaint.setColor(Color.parseColor(mColor));
        } else {
            strokePaint.setColor(Color.BLACK);
        }
        strokePaint.setStrokeWidth(mBorder);

        canvas.drawRoundRect(new RectF(mMargin, mMargin, right, bottom), mRadius, mRadius, paint);

        // stroke
        canvas.drawRoundRect(new RectF(mMargin, mMargin, right, bottom), mRadius, mRadius, strokePaint);
    }


    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }

    public String getId() {
        return "RoundedTransformation(radius=" + mRadius + ", margin=" + mMargin + ", diameter="
                + mDiameter + ", cornerType=" + mCornerType.name() + ")";
    }
}