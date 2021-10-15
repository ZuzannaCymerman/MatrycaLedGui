#include <EEPROM.h>
#include <Arduino.h>

int writeNumberToMemory(int offset, int number)
{
  char numberByte = (char)number;
  EEPROM.write(offset, numberByte);
  return offset+1;
}

int readNumberFromMemory(int offset)
{
  char numberByte = EEPROM.read(offset);
  int number = (int)numberByte;
  return number;
}

void clearMemory(){
   for (int i = 0 ; i < EEPROM.length() ; i++) {
    EEPROM.write(i, 0);
  }
}

bool MemoryEmpty(){
  int state = 0;
  for (int i = 0 ; i < EEPROM.length() ; i++) {
    state|= EEPROM.read(0+i);
  }
  if(state == 0){
    return true;
  }
  else{
    return false;
  }  
}
