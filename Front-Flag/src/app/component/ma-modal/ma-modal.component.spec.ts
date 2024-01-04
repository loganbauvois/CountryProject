import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaModalComponent } from './ma-modal.component';

describe('MaModalComponent', () => {
  let component: MaModalComponent;
  let fixture: ComponentFixture<MaModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MaModalComponent]
    });
    fixture = TestBed.createComponent(MaModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
