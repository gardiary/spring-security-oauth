import { Component } from '@angular/core';
import {AppService, Foo} from './app.service'

@Component({
  selector: 'foos-details',
  providers: [AppService],  
  template: `<div class="container">
    <h1 class="col-sm-12">Foos Details</h1>
    <div class="col-sm-12">
        <table>
            <thead>
            <tr>
                <th style="width: 40px;">ID</th>
                <th style="width: 100px;">Name</th>
            </tr>
            </thead>
            <tbody>
                <tr *ngFor="let foo of foos">
                    <td>{{foo.id}}</td>
                    <td>{{foo.name}}</td>
                </tr>
            </tbody>
        </table>
    </div>
    
    <div class="col-sm-12">
        <button class="btn btn-primary" (click)="getFoos()" type="button">List all Foos (Superuser only)</button>        
    </div>
</div>`
})

export class FoosComponent {
    public foos: Foo[];
    private foosUrl = 'http://localhost:8081/resource-server/api/secured/foos';  

    constructor(private _service:AppService) {}

    getFoos(){
        this._service.getResource(this.foosUrl)
         .subscribe(data => this.foos = data);
    }
}
