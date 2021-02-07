package com.jmenterprise.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.jmenterprise.mediaplayer.R.raw.gnash__i_hate_u_i_love_u;

public class MainActivity extends AppCompatActivity {

    //crie o atributo do mediaPlayer
    private MediaPlayer mediaPlayer;
    //crie o atributo para o SeekBar
    private SeekBar seekVolume;
    //crie o atributo do gerenciador de audio
    private AudioManager audioManager;

    private int[] musicas = new int[5];
    private int musicaAtual = 0;
    private int totalMusicas = musicas.length;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        musicas[0] = R.raw.bach;
        musicas[1] = R.raw.charlie_puth__we_dont_talk_anymore;
        musicas[2] = gnash__i_hate_u_i_love_u;
        musicas[3] = R.raw.lil_lixo_imortal;
        musicas[4] = R.raw.teste;






        //carregando a musica anteriormente
        //recebe dois parametros para a musica configurada
        mediaPlayer = MediaPlayer.create(getApplicationContext(), musicas[musicaAtual]);
        inicializarSeekBar();


    }

    //criando o método do seek bar
    private void inicializarSeekBar(){
        //recuperando a referência do seekBar
        seekVolume = findViewById(R.id.seekVolume);

        //configurando o audio manager
        //precisamos saber o volume máximo e atual do usuário
        //recuperando o contexto que tem acesso ao serviço de audio
        //também é necessário fazer o cast para (Audio Manager)
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //recuperando o volume máximo e o volume atual
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //configurando os valores máximos e atuais para o seekBar
        seekVolume.setMax(volumeMaximo);
        seekVolume.setProgress(volumeAtual);

        //agoras criando o listener para que possa mudar o volume em tempo real
        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //nesse caso usaremos apenas o seek Progress Changed
                //primeiro parametro é o tipo de audio
                //segundo passando o indice do volume (progress bar)
                //no flag que é o terceira parâmetro pode-se definir formas de mudar o volume
                //nesse caso o AudioManager.FLAG_SHOW_UI ele mostra junto com o UI do proprio celular
                //porém se colocar "0" ela não faz nenhuma configuração adicional
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,
                        AudioManager.FLAG_SHOW_UI);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    //criando os métodos para execução de midia
    public void executarSom(View view){
        //primeiro verificar se a música realmente existe
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    //método para pausar a música
    public void pausarMusica(View view){
        //primeiro eu preciso saber se a música está sendo executada
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    //método parando música
    public void pararMusica(View view){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            //porém quando para a música ele perde a referência inicial
            //com isso precisamos referencia-la novamente
            mediaPlayer = MediaPlayer.create(getApplicationContext(), musicas[musicaAtual]);
        }
    }

    //método para quando a activity for destruida


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            //o método release, libera os recursos de memória
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //métoda para pausar música caso deixe o aplicativo em segundo plano
    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void proximaMusica(View view){
        if (mediaPlayer.isPlaying() && musicaAtual < 4){
            mediaPlayer.stop();
            musicaAtual += 1;
            //porém quando para a música ele perde a referência inicial
            //com isso precisamos referencia-la novamente
            mediaPlayer = MediaPlayer.create(getApplicationContext(), musicas[musicaAtual]);
            mediaPlayer.start();
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "Não há mais músicas",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void musicaAnterior(View view){
        if (mediaPlayer.isPlaying() && musicaAtual > 1){
            mediaPlayer.stop();
            musicaAtual -= 1;
            //porém quando para a música ele perde a referência inicial
            //com isso precisamos referencia-la novamente
            mediaPlayer = MediaPlayer.create(getApplicationContext(), musicas[musicaAtual]);
            mediaPlayer.start();
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "Primeira música da Lista",
                    Toast.LENGTH_SHORT).show();
        }
    }


}