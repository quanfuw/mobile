//
//  Simple.h
//  ChapThree
//
//  Created by exo on 2/2/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Simple : NSObject {
}

+ (void) sayGoodBye;
- (id) initWithInt: (int) value;
- (void) sayHello: (NSString*) name;
- (void) sayHelloTom;
@end
