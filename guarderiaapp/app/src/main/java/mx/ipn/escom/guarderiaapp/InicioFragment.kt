package mx.ipn.escom.guarderiaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.guarderiaapp.databinding.FragmentInicioBinding

class InicioFragment : Fragment() {
    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!

    //Coneccion a la base de datos
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)


        binding.Nombrealumno.setText("Julio Cesar")


        val user = FirebaseAuth.getInstance().currentUser


        // Inflate the layout for this fragment
        return  binding.root
    }

}