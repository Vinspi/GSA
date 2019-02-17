import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoremComponent } from './components/lorem/lorem.component'
import { ConnectionFormComponent } from './components/connection-form/connection-form.component'
import { StockOverviewComponent } from './components/stock-overview/stock-overview.component'
import { WithdrawComponent } from './components/withdraw/withdraw.component';
import { StatsComponent } from './components/stats/stats.component';
import { AddAliquoteComponent } from './components/add-aliquote/add-aliquote.component';


const routes: Routes = [
  {path: 'lorem', component: LoremComponent},
  {path: 'login', component: ConnectionFormComponent},
  {path: 'stockoverview', component: StockOverviewComponent},
  {path: 'withdraw', component: WithdrawComponent},
  {path: 'stats', component: StatsComponent},
  {path: 'add-aliquote', component: AddAliquoteComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
