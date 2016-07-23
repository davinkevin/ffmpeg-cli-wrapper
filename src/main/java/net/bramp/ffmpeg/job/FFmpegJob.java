package net.bramp.ffmpeg.job;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.progress.ProgressListener;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * @author bramp
 *
 */
public abstract class FFmpegJob implements Runnable {

  public enum State {
    WAITING, RUNNING, FINISHED, FAILED,
  }

  final FFmpeg ffmpeg;
  final FFprobe ffprobe;
  final ProgressListener listener;

  State state = State.WAITING;

  public FFmpegJob(FFmpeg ffmpeg, FFprobe ffprobe) {
    this(ffmpeg, ffprobe, null);
  }

  public FFmpegJob(FFmpeg ffmpeg, FFprobe ffprobe, @Nullable ProgressListener listener) {
    this.ffmpeg = checkNotNull(ffmpeg);
    this.ffprobe = checkNotNull(ffprobe);
    this.listener = listener;
  }

  public State getState() {
    return state;
  }
}
