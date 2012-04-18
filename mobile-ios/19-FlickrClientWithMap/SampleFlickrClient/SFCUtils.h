//
//  SFCUtils.h
//  SampleFlickrClient
//
//  Created by exo on 2/27/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

static NSString* const LATITUDE = @"latitude";
static NSString* const LONGITUDE = @"longitude";
static NSString* const PLACE_CONTENT = @"_content";
//#define PLACE_CONTENT "_content"

@interface SFCUtils : NSObject

+ (NSString*)getTitleOfPhoto:(NSDictionary*)photo;
+ (NSString*)getDescriptionOfPhoto:(NSDictionary*)photo;
+ (NSString*)getCityNameOfPlace:(NSDictionary*)place;
+ (NSString*)getLocationOfPlace:(NSDictionary*)place;
@end
