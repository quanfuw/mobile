//
//  MemManPolicy.m
//  MemManagement
//
//  Created by exo on 2/6/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "MemManPolicy.h"
#import "Person.h"

@implementation MemManPolicy

- (void)rule {
    // You own any object you create
    NSLog(@"Creating person ...");
    Person *person = [[Person alloc] init];
    person.firstName = @"QUANG";
    person.lastName = @"LE";
    
    // Use autorelease to Send a Deferred release
    NSLog(@"Testing autorelease ... %@", [person fullname]);
    
    // When you no longer need it, you must relinquish ownership of an object you own
    NSLog(@"Relinquish ownership of 'person' ...");
    
    // You must not relinquish ownership of an object you do not own
    NSURL *url = [[NSURL alloc] initFileURLWithPath:@"http://google.com"];
    // Do not release error
    NSError *error = nil;
    NSString *content = [[NSString alloc] initWithContentsOfURL:url encoding:NSUTF8StringEncoding error:&error];
    [url release];
    [content release];
    [person release];
}
@end
