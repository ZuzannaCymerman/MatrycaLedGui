
#include "SoftwareSerial.h"
#include "WiFiEsp.h"
#include "ArduinoJson.h"
#include "EEPROM.h"

SoftwareSerial EspSerial(10, 11);
WiFiEspServer server(80);
int status = WL_IDLE_STATUS;

int* ledNumbers;
int* ledColors;
 
void setup() {
  
  Serial.begin(9600);
  EspSerial.begin(9600);
  
  WiFi.init(&EspSerial);
  
  status = WiFi.beginAP("LedMatrix");
  
  IPAddress local_IP(192, 168, 0, 190);
  WiFi.configAP(local_IP);
  Serial.println(WiFi.localIP());
  server.begin();
}

void loop() {

   WiFiEspClient client = server.available();
   int *ledNumbers;
   int *ledColors;

 if (client)
    {
        Serial.println("A client has connected");

        while (client.connected())
        {
            if (client.available())
            {
              Serial.println("connected");
              client.readStringUntil('|');
              String action = client.readStringUntil('|');

              Serial.println(action);
              
              if(action == "V"){
                String ledQuantityStr = client.readStringUntil('|');
  
                const char * ledQuantityChar = ledQuantityStr.c_str();
                int ledQuantity = atoi(ledQuantityChar);
  
                ledNumbers = new int[ledQuantity];
                ledColors = new int[ledQuantity];    
                
                           
                for(int i=0;i<ledQuantity;i++){
                  String dataStr = client.readStringUntil('|');
                  const char * dataChar = dataStr.c_str();
                  ledNumbers[i] = atoi(dataChar);
                }
                
                for(int i=0;i<ledQuantity;i++){
                  String dataStr = client.readStringUntil('|');
                  const char * dataChar = dataStr.c_str();
                  ledColors[i] = atoi(dataChar);
                }

                Serial.println(ledNumbers[2]);
                Serial.println(ledColors[2]);
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
           

  
