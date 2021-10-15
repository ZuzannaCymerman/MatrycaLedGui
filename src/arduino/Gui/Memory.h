#ifndef View_H
#define Views_H
#include <EEPROM.h>
#include <Arduino.h>

int writeNumberToMemory(int addrOffset, int number);
int readNumberFromMemory(int addrOffset);
void clearMemory();
bool MemoryEmpty();

#endif
