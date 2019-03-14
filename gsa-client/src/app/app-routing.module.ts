import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { AdminGuard } from './guards/admin.guard';
import { MaintenanceGuard } from './guards/maintenance.guard';

import { ConnectionFormComponent } from './components/connection-form/connection-form.component'
import { StockOverviewComponent } from './components/stock-overview/stock-overview.component'
import { WithdrawComponent } from './components/withdraw/withdraw.component';
import { AlertsPanelComponent } from './components/alerts-panel/alerts-panel.component';
import { InventoryComponent } from './components/inventory/inventory.component';
import { ProductManagerComponent } from './components/product-manager/product-manager.component';
import { AdminHomepageComponent } from './components/admin-homepage/admin-homepage.component';
import { MaintenanceComponent } from './components/maintenance/maintenance.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { AdminMaintenanceComponent } from './components/admin-maintenance/admin-maintenance.component';
import { OutdatedManagementComponent } from './components/outdated-management/outdated-management.component';

const routes: Routes = [
  {path: '', canActivate: [AdminGuard, MaintenanceGuard], component: AdminHomepageComponent},
  {path: 'login', component: ConnectionFormComponent},
  {path: 'stockoverview', canActivate: [MaintenanceGuard], component: StockOverviewComponent},
  {path: 'withdraw', canActivate: [AuthGuard, MaintenanceGuard],component: WithdrawComponent},
  {path: 'alerts', canActivate: [AdminGuard, MaintenanceGuard], component: AlertsPanelComponent},
  {path: 'inventory', canActivate: [AdminGuard, MaintenanceGuard], component: InventoryComponent},
  {path: 'product-manager', canActivate: [AdminGuard, MaintenanceGuard], component: ProductManagerComponent},
  {path: 'maintenance', component: MaintenanceComponent},
  {path: 'admin-maintenance', canActivate: [AdminGuard], component: AdminMaintenanceComponent},
  {path: 'outdated-manager', canActivate:[AdminGuard, MaintenanceGuard], component: OutdatedManagementComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
