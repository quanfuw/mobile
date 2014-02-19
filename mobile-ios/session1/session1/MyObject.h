//
//  MyObject.h
//  session1
//
//  Created by Pham Tuan on 2/11/14.
//  Copyright (c) 2014 Pham Tuan. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MyProtocol.h"

@interface MyObject : NSObject <MyProtocol>{
  int _myInt;
 
}
- (void)setMyInt:(int)myInt;
- (int)getMyInt;


@end
