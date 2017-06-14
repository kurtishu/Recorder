package com.dreamfactory.recorder.recorder;


public class RecorderClient {

    private final int mAudioSource;
    private final int mOutputFormat;
    private final String mOutputFile;
    private final int mAudioEncoder;

    private RecorderClient(Builder builder) {
        mAudioSource = builder.mAudioSource;
        mOutputFormat= builder.mOutputFormat;
        mOutputFile = builder.mOutputFile;
        mAudioEncoder = builder.mAudioEncoder;
    }




    public static final class Builder {

        private int mAudioSource;
        private int mOutputFormat;
        private String mOutputFile;
        private int mAudioEncoder;

        public Builder setAudioSource(int audioSource) {
            mAudioSource = audioSource;
            return this;
        }

        public Builder setOutputFormat(int outputFormat) {
            mOutputFormat = outputFormat;
            return this;
        }

        public Builder setOutputFile(String outputFile) {
            mOutputFile = outputFile;
            return this;
        }

        public Builder setAudioEncoder(int audioEncoder) {
            mAudioEncoder = audioEncoder;
            return this;
        }

        public RecorderClient build() {
          return new RecorderClient(this);
        }
    }
}
