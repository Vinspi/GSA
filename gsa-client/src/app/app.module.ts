import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DataTablesModule } from 'angular-datatables';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MenuComponent } from './components/menu/menu.component';
import { LoremComponent } from './components/lorem/lorem.component';
import { ConnectionFormComponent } from './components/connection-form/connection-form.component';
import { StockOverviewComponent } from './components/stock-overview/stock-overview.component';
import { WithdrawComponent } from './components/withdraw/withdraw.component';
import { StatsComponent } from './components/stats/stats.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { AddAliquoteComponent } from './components/add-aliquote/add-aliquote.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    MenuComponent,
    LoremComponent,
    ConnectionFormComponent,
    StockOverviewComponent,
    WithdrawComponent,
    StatsComponent,
    AddProductComponent,
    StatsComponent,
    AddAliquoteComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    DataTablesModule,
    ChartsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
