package com.example.napni.mallfinder.map_data

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class DataDownloader {

    private var line:String? = ""

    fun readUrl(data_url : String) : String {
        var data = ""
        lateinit var inputStream: InputStream
        lateinit var urlConnection : HttpURLConnection

        try {
            urlConnection = URL(data_url).openConnection() as HttpURLConnection
            urlConnection.connect()
            inputStream = urlConnection.inputStream
            val br = BufferedReader(InputStreamReader( inputStream ) )
            val sb = StringBuffer()

            while( readLine(br) )
            {
                sb.append(line);
            }

            data = sb.toString()
            br.close()
        } catch (mf : MalformedURLException) {
            mf.printStackTrace()
        } catch (io : IOException) {
            io.printStackTrace()
        } finally {
            inputStream.close()
            urlConnection.disconnect()
        }

        line = ""
        return data
    }

    private fun readLine(br : BufferedReader) : Boolean {
        line = br.readLine()
        if( line == null ) return false
        return true
    }
}