//
//  AImagePickerController.m
//  DatePicker
//
//  Created by exo on 2/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "AImagePickerController.h"

@implementation AImagePickerController

@synthesize selectedImage = _selectedImage;
@synthesize theImageView = _theImageView;
@synthesize takePhotoButton = _takePhotoButton;

- (void)dealloc {
    [_selectedImage release];
    [_theImageView release];
    [_takePhotoButton release];
    [super dealloc];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.tabBarItem.title = @"Image picker";
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.takePhotoButton.enabled = [UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(changeImage) name:@"ImagePicked" object:nil];
}

- (void)viewDidUnload
{
    [self setTheImageView:nil];
    [self setTakePhotoButton:nil];
    [super viewDidUnload];
    self.selectedImage = nil;
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)selectPicture:(id)sender {
    UIImagePickerController *pickCont = [[UIImagePickerController alloc] init];
    pickCont.delegate = self;
    pickCont.allowsEditing = YES;
    pickCont.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
    [self presentModalViewController:pickCont animated:YES];
    
}

- (IBAction)takePhoto:(id)sender {
    UIImagePickerController * pickCont = [[UIImagePickerController alloc] init];
    pickCont.delegate = self;
    pickCont.allowsEditing = YES;
    pickCont.sourceType = UIImagePickerControllerSourceTypeCamera;
    [self presentModalViewController:pickCont animated:YES];
    NSLog(@"heynow");
    if(self.selectedImage != nil)
        self.theImageView.image = self.selectedImage;
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker {
    [picker.parentViewController dismissModalViewControllerAnimated:YES];
    [picker release];
}

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
    self.selectedImage = (UIImage*) [info objectForKey:UIImagePickerControllerOriginalImage];
    [[NSNotificationCenter defaultCenter] postNotificationName:@"ImagePicked" object:nil];
    [picker.parentViewController dismissModalViewControllerAnimated:YES];
    [picker release];
}

- (void)changeImage {
    self.theImageView.image = self.selectedImage;
}

@end
