//
//  ViewController.m
//  Examination
//
//  Created by Sergey Gomanyuk on 17.09.14.
//  Copyright (c) 2014 sergg. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
    NewClass *newClass = [[NewClass alloc]init];
    BOOL total = [newClass ExaminationCount: 85];
    NSLog(total ? @"ЕГЭ сдано на 5!" : @"Пятерки не светит!");
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
