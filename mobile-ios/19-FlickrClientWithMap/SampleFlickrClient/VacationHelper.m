//
//  VacationHelper.m
//  SampleFlickrClient
//
//  Created by exo on 3/6/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "VacationHelper.h"

static NSMutableDictionary *managedDocuments;

@implementation VacationHelper

+ (NSArray *)getVacations {
    NSURL *documentsUrl = [VacationHelper applicationDocumentsDirectory];
    NSMutableArray *array = [[[NSMutableArray alloc] init] autorelease];
    NSArray *vacations = [[NSFileManager defaultManager] contentsOfDirectoryAtURL:documentsUrl includingPropertiesForKeys:[NSArray arrayWithObject:NSURLIsDirectoryKey] options:NSDirectoryEnumerationSkipsHiddenFiles error:nil];
    // get list of vacation data in the "VacationsStore" folder
    for (NSURL *url in vacations) {
        NSString *typeIdentifier;
        if ([url getResourceValue:&typeIdentifier forKey:NSURLIsDirectoryKey error:nil]) {
            [array addObject:[url lastPathComponent]];            
        }

    }
    return [NSArray arrayWithArray:array];
}


+ (UIManagedDocument *)sharedManagedDocumentForVacation:(NSString *)vacationName {
    if (!managedDocuments) {
        managedDocuments = [[NSMutableDictionary alloc] init];
    }
    UIManagedDocument *managedDocument = [managedDocuments objectForKey:vacationName];
    if (!managedDocument) {
        NSURL *url = [VacationHelper applicationDocumentsDirectory];
        managedDocument = [[UIManagedDocument alloc] initWithFileURL:[url URLByAppendingPathComponent:vacationName]];
        [managedDocuments setValue:managedDocument forKey:vacationName];
        [managedDocument release];
    }
    return managedDocument;
}

+ (void)openVacation:(NSString *)vacationName usingBlock:(completion_block_t)block {
    UIManagedDocument *managedDocument = [VacationHelper sharedManagedDocumentForVacation:vacationName];
    NSFileManager *fileManager = [NSFileManager defaultManager];
    if ([fileManager fileExistsAtPath:managedDocument.fileURL.path]) {
        if (managedDocument.documentState == UIDocumentStateClosed) {
            [managedDocument openWithCompletionHandler:^(BOOL success) {
                if (success)
                    block(managedDocument); 
                else NSLog(@"couldn’t open document at %@", managedDocument.fileURL);
            }];
        } else if (managedDocument.documentState == UIDocumentStateNormal) {
            block(managedDocument);
        }
    } else {
        [managedDocument saveToURL:managedDocument.fileURL forSaveOperation:UIDocumentSaveForCreating completionHandler:^(BOOL success) {
            if (success)
                block(managedDocument);
            else NSLog(@"couldn’t open document at %@", managedDocument.fileURL);
        }];
    }
}

+ (NSURL *)applicationDocumentsDirectory {
    NSURL *url = [[[[NSFileManager defaultManager] URLsForDirectory:NSDocumentDirectory inDomains:NSUserDomainMask] lastObject] URLByAppendingPathComponent:@"VacationsStore"];
    if (![[NSFileManager defaultManager] fileExistsAtPath:url.path]) {
        [[NSFileManager defaultManager] createDirectoryAtPath:url.path withIntermediateDirectories:YES attributes:nil error:nil];
    }
    return url;
}

@end
