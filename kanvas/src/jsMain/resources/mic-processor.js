class MicProcessor extends AudioWorkletProcessor {
  process(inputs, outputs) {
    const input = inputs[0];
    if (input && input[0]) {
      // Send recorded audio samples (Float32Array) back to main thread
      this.port.postMessage(input[0]);
    }
    return true; // keep alive
  }
}

registerProcessor('mic-processor', MicProcessor);