import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './services/auth.guard';
import { AdminGuard } from './services/admin.guard';

import { ConnectionFormComponent } from './components/connection-form/connection-form.component'
import { StockOverviewComponent } from './components/stock-overview/stock-overview.component'
import { WithdrawComponent } from './components/withdraw/withdraw.component';
import { AlertsPanelComponent } from './components/alerts-panel/alerts-panel.component';
import { InventoryComponent } from './components/inventory/inventory.component';
import { ProductManagerComponent } from './components/product-manager/product-manager.component';
import { AdminHomepageComponent } from './components/admin-homepage/admin-homepage.component';
import { EditReportComponent } from './components/edit-report/edit-report.component';


const routes: Routes = [
  {path: '', canActivate: [AdminGuard], component: AdminHomepageComponent},
  {path: 'login', component: ConnectionFormComponent},
  {path: 'stockoverview', component: StockOverviewComponent},
  {path: 'withdraw', canActivate: [AuthGuard],component: WithdrawComponent},
  {path: 'alerts', canActivate: [AdminGuard], component: AlertsPanelComponent},
  {path: 'inventory', canActivate: [AdminGuard], component: InventoryComponent},
  {path: 'product-manager', canActivate: [AdminGuard], component: ProductManagerComponent},
  {path: 'editreport', canActivate: [AdminGuard], component: EditReportComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
