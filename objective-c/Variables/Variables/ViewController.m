//
//  ViewController.m
//  Variables
//
//  Created by Sergey Gomanyuk on 17.09.14.
//  Copyright (c) 2014 sergg. All rights reserved.
//

#import "ViewController.h"
#import "NewClass.h"

enum coeffic {kg = 1000, tn = 1000000};

int summ = 50;

@interface ViewController ()

@end

@implementation ViewController

@synthesize megaVar;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
    int v = 10;
    
    if (v==10) {
        v ++;
        summ = 50;
    }
    
    if (v==11) {
        v+=1;
        summ = 50;
    }
    if (v==12) {
        [self summir];
        NSLog(@"summ = %d", summ);
        [self globalir];
        megaVar = @"Самая глобальная переменная";
        [self megair];
    }
    
    NewClass *newClass = [[NewClass alloc]init];

    [newClass setGramm:1000000];
    [newClass tonnConvert:1000000];
    [newClass ConvertationKilogramm:1000 ConvertationTonn:1000000 Weight:2000000];
    
    [newClass ConvertationKilogramm: (enum coeffic)kg ConvertationTonn:(enum coeffic)tn Weight:3500000];
    
    enum coeffic kilo = kg;
    enum coeffic tonn = tn;
    [newClass ConvertationKilogramm: kilo ConvertationTonn:tonn Weight:4000000];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - my functions

- (void) summir {
    summ ++;
}

- (void) globalir {
    globalVar = @"Глобальная переменная";
    NSLog(@"globalVar = %@", globalVar);
}

- (void) megair {
    NSLog(@"megaVar = %@", megaVar);
}

@end
