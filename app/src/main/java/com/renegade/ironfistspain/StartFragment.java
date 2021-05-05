package com.renegade.ironfistspain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.renegade.ironfistspain.databinding.FragmentStartBinding;

public class StartFragment extends BaseFragment {

    private FragmentStartBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return (binding = FragmentStartBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.registroButton.setOnClickListener(v -> {
            nav.navigate(R.id.action_startFragment_to_registroFragment);
        });
    }



}

/*

<FrameLayout>
    <ConstraintLayout id="formularioInicial">
        <GButton>
        <GButton>
    </ConstrintLayout>

    <ProgressBar id="pb">
</Layout>

 */