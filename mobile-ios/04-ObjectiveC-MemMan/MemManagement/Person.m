//
//  Person.m
//  MemManagement
//
//  Created by exo on 2/6/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Person.h"

@implementation Person
@synthesize firstName;
@synthesize lastName;

- (NSString *)fullname {
    // Use autorelease to Send a Deferred release
    NSString *string = [[[NSString alloc] initWithFormat:@"%@ %@", firstName, lastName] autorelease];
    return string;
}

- (void)dealloc {
    // Implement dealloc to Relinquish Ownership of Objects
    [firstName release];
    [lastName release];
    [super dealloc];
}
@end
