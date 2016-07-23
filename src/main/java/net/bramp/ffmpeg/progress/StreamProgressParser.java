package net.bramp.ffmpeg.progress;

import com.google.common.base.Charsets;

import java.io.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class StreamProgressParser {

  final ProgressListener listener;
  final double duration;

  public StreamProgressParser(ProgressListener listener, double duration) {
    this.listener = checkNotNull(listener);
    this.duration = duration;
  }

  private static BufferedReader wrapInBufferedReader(Reader reader) {
    checkNotNull(reader);

    if (reader instanceof BufferedReader)
      return (BufferedReader) reader;

    return new BufferedReader(reader);
  }

  public void processStream(InputStream stream) throws IOException {
    checkNotNull(stream);
    processReader(new InputStreamReader(stream, Charsets.UTF_8));
  }

  public void processReader(Reader reader) throws IOException {
    final BufferedReader in = wrapInBufferedReader(reader);

    String line;
    Progress p = new Progress(duration);
    while ((line = in.readLine()) != null) {
      if (p.parseLine(line)) {
        listener.progress(p);
        p = new Progress(duration);
      }
    }
  }

}
