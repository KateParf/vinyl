import { bootstrapApplication } from '@angular/platform-browser';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { AppModule } from './app/app.module';


//bootstrapApplication(AppComponent, appConfig)
//.catch((err) => console.error(err));

export function getBaseUrl() {
  return document.getElementsByTagName('base')[0].href;
}

const providers = [
  { provide: 'BASE_URL', useFactory: getBaseUrl, deps: [] }
];


platformBrowserDynamic(providers).bootstrapModule(AppModule)
  .catch(err => console.log(err));
