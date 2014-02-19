//
//  main.m
//  session1
//
//  Created by Pham Tuan on 2/11/14.
//  Copyright (c) 2014 Pham Tuan. All rights reserved.
//
#include <stdio.h>
#include <Foundation/Foundation.h>
int main(int argc, const char **argv)
{
  NSString *message = @"Hello World!";
  printf("%s\n", [message cString]);
  return 0;
}