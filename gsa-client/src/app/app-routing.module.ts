import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './services/auth.guard';
import { AdminGuard } from './services/admin.guard';

import { LoremComponent } from './components/lorem/lorem.component'
import { ConnectionFormComponent } from './components/connection-form/connection-form.component'
import { StockOverviewComponent } from './components/stock-overview/stock-overview.component'
import { WithdrawComponent } from './components/withdraw/withdraw.component';
import { StatsComponent } from './components/stats/stats.component';

const routes: Routes = [
  {path: 'lorem', component: LoremComponent},
  {path: 'login', component: ConnectionFormComponent},
  {path: 'stockoverview', component: StockOverviewComponent},
  {path: 'withdraw', canActivate: [AuthGuard],component: WithdrawComponent},
  {path: 'stats', canActivate: [AdminGuard], component: StatsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
