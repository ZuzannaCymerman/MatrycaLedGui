
#include "SoftwareSerial.h"
#include "WiFiEsp.h"
#include "Views.h"
#include "VirtualWire.h"


#define analogPin A2
#define strobePin 22
#define resetPin 24

#define RFpin 50

#define LED_COUNT 200
#define LED_PIN 45

SoftwareSerial EspSerial(10, 11);
WiFiEspServer server(80);
int status = WL_IDLE_STATUS;
Adafruit_NeoPixel pixels(LED_COUNT,LED_PIN, NEO_RGB+NEO_KHZ800);
String action;
int *ledNumbers;
int *ledColors;
int ledQuantity;
 
void setup() {
  Serial.begin(9600);
  EspSerial.begin(9600);
  
  //pixels.begin();
  //pixels.setBrightness(20);
  
  //vw_set_rx_pin(RFpin);
  //vw_setup(2000);
  //vw_rx_start();


  analogReference(DEFAULT);
  //pinMode(analogPin, INPUT);
 // pinMode(strobePin, OUTPUT);
 // pinMode(resetPin, OUTPUT);
 // pinMode(LED_PIN, OUTPUT);

  //digitalWrite(resetPin, LOW);
  //digitalWrite(strobePin, HIGH);
  
  WiFi.init(&EspSerial);
  
  status = WiFi.beginAP("LedMatrix");
  
  IPAddress local_IP(192, 168, 0, 190);
  WiFi.configAP(local_IP);
  Serial.println(WiFi.localIP());
  
  server.begin();
}

void loop() {



action = "V";
 
  while(action == "V"){
    showView(ledNumbers,ledColors, pixels, ledQuantity);
    WiFiEspClient client = server.available();
    if (client)
      {
          Serial.println("A client has connected");
  
          while (client.connected()){
            
              if (client.available()){
                
                Serial.println("connected");
                client.readStringUntil('|');
                action = client.readStringUntil('|');
  
                Serial.println(action);
                
                if(action == "V"){
  
                   ledQuantity = receiveLedQuantity(client);
                    
                   ledNumbers = new int[ledQuantity];
                   ledColors = new int[ledQuantity];    
                  
                   receiveView(ledNumbers, ledColors, client, ledQuantity);
    
                   Serial.println(ledNumbers[2]);
                   
                 }else if (action == "S"){
                    pixels.clear();
                    Serial.println("stop");
                    ledColors = new int[ledQuantity]; 
                    action = "V";
                 }
               
                  client.print(
                        "HTTP/1.1 200 OK\r\n"
                        "Connection: close\r\n"
                        "\r\n");
                       
                  client.stop();
                  
                  }
              }
  
          delay(10);
          client.stop();
          Serial.println("Client disconnected");
        } 
  }
 
     
}
           

  
