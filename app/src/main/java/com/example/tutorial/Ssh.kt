package com.example.tutorial

import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class Ssh {
    fun executeCommand(host: String?, username: String?, password: String?, command: String?): String {
        val jsch = JSch()
        var salida = ""
        try {
            val session = jsch.getSession(username, host, 22)

            // Configuración de la sesión (puedes ajustarla según tus necesidades)
            val config = java.util.Properties()

            // Abrir un canal SSH
            val stringBuilder = StringBuilder()

            config["StrictHostKeyChecking"] = "no"
            session.setConfig(config)
            session.setPassword(password)
            Thread {
                //Do some Network Request
                session.connect()
                val channel = session.openChannel("exec") as ChannelExec
                // Establecer el comando a ejecutar
                channel.setCommand(command)

                // Obtener el InputStream para leer la salida del comando
                val inputStream: InputStream = channel.inputStream
                // Conectar el canal
                channel.connect()
                // Leer la salida del comando
                val reader = BufferedReader(InputStreamReader(inputStream))

                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) break
                    stringBuilder.append(line).append("\n")
                }

                // Desconectar todo
                channel.disconnect()
                session.disconnect()
            }.start()
            salida = stringBuilder.toString()
        // Devolver la salida del comando
        }
        catch (e: Exception){
         
            salida = "Mensaje de excepción no disponible"
        }
        return salida
    }
}