import { Subject } from 'rxjs';
import { OnInit } from '@angular/core';
import { User } from './user';

export class Foo {

    public static subj: Subject<User> = new Subject();

    constructor() { }



}