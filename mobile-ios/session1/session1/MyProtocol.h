//
//  MyProtocol.h
//  session1
//
//  Created by Pham Tuan on 2/11/14.
//  Copyright (c) 2014 Pham Tuan. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol MyProtocol <NSObject>
@property (assign, nonatomic) int myInt;
- (NSString *)stringMyInt;

@end
