class PlayerProcessor extends AudioWorkletProcessor {
  constructor() {
    super();
    this.buffer = [];
    this.paused = false;

    this.port.onmessage = (event) => {
      const msg = event.data;
      if (msg === "pause") this.paused = true;
      else if (msg === "resume") this.paused = false;
      else if (msg instanceof Float32Array) this.buffer.push(msg);
    };
  }

  process(inputs, outputs) {
    const output = outputs[0];
    const channel = output[0];

    if (this.paused || this.buffer.length === 0) {
      channel.fill(0); // silence
    } else {
      const chunk = this.buffer.shift();
      const len = Math.min(channel.length, chunk.length);
      for (let i = 0; i < len; i++) channel[i] = chunk[i];
      for (let i = len; i < channel.length; i++) channel[i] = 0.0;
    }

    return true;
  }
}

registerProcessor("player-processor", PlayerProcessor);