import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DataTablesModule } from 'angular-datatables';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MenuComponent } from './components/menu/menu.component';
import { ConnectionFormComponent } from './components/connection-form/connection-form.component';
import { StockOverviewComponent } from './components/stock-overview/stock-overview.component';
import { WithdrawComponent } from './components/withdraw/withdraw.component';
import { StatsComponent } from './components/stats/stats.component';
import { AddProductComponent } from './components/product-manager/add-product/add-product.component';
import { AddAliquoteComponent } from './components/product-manager/add-aliquote/add-aliquote.component';
import { HistoryComponent } from './components/history/history.component';
import { AlertsPanelComponent } from './components/alerts-panel/alerts-panel.component';
import { EditAlertsComponent } from './components/alerts-panel/edit-alerts/edit-alerts.component';
import { TriggeredAlertsComponent } from './components/alerts-panel/triggered-alerts/triggered-alerts.component';
import { AddAlertComponent } from './components/alerts-panel/add-alert/add-alert.component';
import { ToastComponent } from './components/toast/toast.component';
import { EditReportComponent } from './components/edit-report/edit-report.component';
import { ReloadableDatatableComponent } from './components/reloadable-datatable/reloadable-datatable.component';

import { InventoryComponent } from './components/inventory/inventory.component';
import { DigitsOnlyDirective } from './directives/digits-only.directive';
import { ProductManagerComponent } from './components/product-manager/product-manager.component';
import { AdminHomepageComponent } from './components/admin-homepage/admin-homepage.component';
import { AlertsNotificationComponent } from './components/admin-homepage/alerts-notification/alerts-notification.component';
import { OutdateNotificationComponent } from './components/admin-homepage/outdate-notification/outdate-notification.component';
import { ReportNotificationComponent } from './components/admin-homepage/report-notification/report-notification.component';
import { StatsProvidersComponent } from './components/admin-homepage/stats-providers/stats-providers.component';
import { StatsProductsComponent } from './components/admin-homepage/stats-products/stats-products.component';
import { MaintenanceComponent } from './components/maintenance/maintenance.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { AdminMaintenanceComponent } from './components/admin-maintenance/admin-maintenance.component';
import { OutdatedManagementComponent } from './components/outdated-management/outdated-management.component';
import { InputMaxLengthDirective } from './directives/input-max-length.directive';
import { NodropDirective } from './directives/nodrop.directive';
import { DigitOnlyDecDirective } from './directives/digit-only-dec.directive';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    MenuComponent,
    ConnectionFormComponent,
    StockOverviewComponent,
    WithdrawComponent,
    StatsComponent,
    AddProductComponent,
    HistoryComponent,
    StatsComponent,
    AddAliquoteComponent,
    AlertsPanelComponent,
    EditAlertsComponent,
    TriggeredAlertsComponent,
    AddAlertComponent,
    ToastComponent,
    EditReportComponent,
    ReloadableDatatableComponent,
    InventoryComponent,
    DigitsOnlyDirective,
    ProductManagerComponent,
    AdminHomepageComponent,
    AlertsNotificationComponent,
    OutdateNotificationComponent,
    ReportNotificationComponent,
    StatsProvidersComponent,
    StatsProductsComponent,
    MaintenanceComponent,
    NotFoundComponent,
    AdminMaintenanceComponent,
    OutdatedManagementComponent,
    InputMaxLengthDirective,
    NodropDirective,
    DigitOnlyDecDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    DataTablesModule,
    ChartsModule,
    NgbModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
