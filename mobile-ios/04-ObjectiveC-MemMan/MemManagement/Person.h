//
//  Person.h
//  MemManagement
//
//  Created by exo on 2/6/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Person : NSObject {
    NSString* firstName;
    NSString* lastName;
}

@property (nonatomic, retain) NSString* firstName;
@property (nonatomic, retain) NSString* lastName;
- (NSString*)fullname;
@end
