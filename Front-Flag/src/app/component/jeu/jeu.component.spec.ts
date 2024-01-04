import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JeuComponent } from './jeu.component';

describe('JeuComponent', () => {
  let component: JeuComponent;
  let fixture: ComponentFixture<JeuComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JeuComponent]
    });
    fixture = TestBed.createComponent(JeuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
