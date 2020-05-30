import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { JhSpell9SharedModule } from 'app/shared/shared.module';
import { JhSpell9CoreModule } from 'app/core/core.module';
import { JhSpell9AppRoutingModule } from './app-routing.module';
import { JhSpell9HomeModule } from './home/home.module';
import { JhSpell9EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    JhSpell9SharedModule,
    JhSpell9CoreModule,
    JhSpell9HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JhSpell9EntityModule,
    JhSpell9AppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class JhSpell9AppModule {}
