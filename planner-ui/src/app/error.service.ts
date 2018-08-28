import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  private errorMessages: Array<string> = new Array<string>();

  constructor() { }

  getErrors(): Array<string> {
    return this.errorMessages;
  }

  addError(errorMessage: string): void {
    this.errorMessages.push(errorMessage);
  }

  clear(): void {
    this.errorMessages = [];
  }
}
