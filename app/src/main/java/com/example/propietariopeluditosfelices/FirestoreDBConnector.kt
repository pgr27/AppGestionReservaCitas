package com.example.propietariopeluditosfelices

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FirestoreDBConnector {
    companion object {
        private val db: FirebaseFirestore by lazy {
            FirebaseFirestore.getInstance()
        }
        private const val TAG = "FireStoreDBConnector";

        fun leerCitas(dia: String, callback: (result: QuerySnapshot?, error: Exception?) -> Unit) {
            // Leer documentos de la colección "Cita"
            db.collection("Citas")
                .whereEqualTo("dia", dia)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(task.result, null)
                    } else {
                        callback(null, task.exception)
                    }
                }
        }
    }
}