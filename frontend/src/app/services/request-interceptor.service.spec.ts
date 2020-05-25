import { TestBed } from '@angular/core/testing';

import { InterceptorService } from './request-interceptor.service';

describe('RequestInterceptorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: InterceptorService = TestBed.get(InterceptorService);
    expect(service).toBeTruthy();
  });
});
