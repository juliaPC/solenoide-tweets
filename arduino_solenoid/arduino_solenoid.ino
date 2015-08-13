#define solenoidPin 11
#define MOVEMENT_TIME 40  // movement semicycle time (ms)
int pending = 0;          // pending cycles
  
// the setup function runs once when you press reset or power the board
void setup() {
  Serial.begin(9600);
  pinMode(solenoidPin, OUTPUT);
}

void cycle() {
    digitalWrite(solenoidPin, 255);
    delay(MOVEMENT_TIME);
    digitalWrite(solenoidPin, 0);
    delay(MOVEMENT_TIME);
}

// the loop function runs over and over again forever
void loop() {
  while (Serial.available()) {
    Serial.read();
    pending++;
  }
  
  while (pending > 0) {
    cycle();
    pending--;
  }
  
}
