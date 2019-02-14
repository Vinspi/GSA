import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoremComponent } from './components/lorem/lorem.component'
import { ConnectionFormComponent } from './components/connection-form/connection-form.component'
import { StockOverviewComponent } from './components/stock-overview/stock-overview.component'

const routes: Routes = [
  {path: 'lorem', component: LoremComponent},
  {path: 'login', component: ConnectionFormComponent},
  {path: 'stockoverview', component: StockOverviewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
