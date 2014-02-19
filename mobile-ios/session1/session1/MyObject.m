//
//  MyObject.m
//  session1
//
//  Created by Pham Tuan on 2/11/14.
//  Copyright (c) 2014 Pham Tuan. All rights reserved.
//

#import "MyObject.h"

@implementation MyObject
- (void)setMyInt:(int)myInt{
  _myInt = myInt;
}
- (int)getMyInt{
  return _myInt;
}
- (id)init {
  return [self initWithInt:0];
}
- (id)initWithInt:(int)myInt {
  if (self = [super init]) {
    _myInt = myInt;
  }
  return self;
}
@end
