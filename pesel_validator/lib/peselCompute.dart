import 'dart:core';

import 'package:intl/intl.dart';



enum Gender {MALE, FEMALE}

List _CONTROLWEIGHTS = [1,3,7,9,1,3,7,9,1,3];


String _getYear(String pesel){
  String century;
  switch(pesel[2]){
    case '8' :
    case '9' : century = "18"; break;
    case '0' :
    case '1' : century = "19"; break;
    case '2' :
    case '3' : century = "20"; break;
    case '4' :
    case '5' : century = "21"; break;
    case '6' :
    case '7' : century = "22"; break;
    default : century = "19"; break;
  }
  return century + pesel[0] + pesel[1];
}
String _getMonth(String pesel){
  String month = pesel[2] + pesel[3];
  if (month[0] != '0' || month[0] != '1'){
    month = '1' + pesel[3];
  }
  return month;
}
String _getDay(String pesel){
  return pesel.substring(4, 6);
}

Gender _getGender(String pesel){
  return int.parse(pesel[9]) % 2 != 0 ? Gender.MALE : Gender.FEMALE;
}

bool _calculateControlSum(String pesel){
  int i = 0;
  int sum = 0;
  for (var weigth in _CONTROLWEIGHTS){
    sum += weigth * (int.parse(pesel[i]) % 10);
    i++;
  }
  int num = 10 - (sum % 10);
  return num == int.parse(pesel[10]);
}


Map getPeselInfo(String pesel){
  var birthDate = new DateFormat("dd-mm-yyyy").parse("${_getDay(pesel)}-${_getMonth(pesel)}-${_getYear(pesel)}");
  var gender = _getGender(pesel);
  var controlSumIsValid = _calculateControlSum(pesel);
  var returnMap = {
  'birthDate': birthDate,
  'gender': gender,
  'controlSumisVaild': controlSumIsValid,
  };
  return returnMap;
}


