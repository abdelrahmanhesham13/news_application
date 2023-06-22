package com.example.feature.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.base.BaseFragment
import com.example.common.BiometricHelper
import com.example.feature.databinding.FragmentLoginBinding

class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        if (BiometricHelper.checkBiometricAvailability(requireContext())) {
            BiometricHelper.showBiometricDialog(
                this,
                onSuccess = {
                    findNavController().navigate(LoginFragmentDirections.actionSplashFragmentToHomeFragment())
                },
                onFailed = {
                    requireActivity().finish()
                }
            )
        } else {
            findNavController().navigate(LoginFragmentDirections.actionSplashFragmentToHomeFragment())
        }
    }


}