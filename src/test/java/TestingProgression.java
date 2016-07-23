import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;

import java.io.IOException;

/**
 * Created by kevin on 23/07/2016.
 */
public class TestingProgression {

    public static void main(String[] args) throws IOException {

        FFmpeg ffmpeg = new FFmpeg("/usr/local/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("/usr/local/bin/ffprobe");

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

        FFmpegBuilder command = new FFmpegBuilder()
                .addInput("http://us-cplus-aka.canal-plus.com/i/1607/14/1173352_140_,200k,400k,800k,1500k,.mp4.csmil/index_3_av.m3u8")
                .addOutput("/tmp/foo.mp4")
                    .setAudioCodec("copy")
                    .setVideoCodec("copy")
                    .setAudioBitStreamFilter("aac_adtstoasc")
                .done();

        executor.createJob(command, new ProgressListener() {
            @Override
            public void progress(Progress p) {
                System.out.printf("%f%%", p.progressionRatio()*100 );
                System.out.println("");
            }
        }).run();
    }

}
