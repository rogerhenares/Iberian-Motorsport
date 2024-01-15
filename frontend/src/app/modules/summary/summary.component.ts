import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppContext } from "../../util/AppContext";
import { trigger, transition, style, animate } from '@angular/animations';

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css'],
  animations: [
    trigger(
      'inOutAnimation',
      [
        transition(
          ':enter',
          [
            style({ opacity: 0 }),
            animate('0.3s ease-out',
              style({ opacity: 1 }))
          ]
        ),
      ]
    )
  ]
})
export class SummaryComponent implements OnInit, AfterViewInit {
  @ViewChild('imagenEspecial') imagenEspecial: ElementRef;

  constructor(
    public router: Router,
    public appContext: AppContext,
  ) {}


  information = [
    { type: 'screen1' },
    { type: 'screen2' },
    { type: 'screen3' }
  ];

  currentIndex = 0;

  showNext() {
    this.currentIndex = (this.currentIndex + 1) % this.information.length;
  }

  showPrev() {
    this.currentIndex = (this.currentIndex - 1 + this.information.length) % this.information.length;
  }


  ngAfterViewInit(): void {
    // Aquí puedes poner el código relacionado con el DOM después de la vista.
  }

  ngOnInit(): void {
    // Aquí puedes poner el código de inicialización.
  }

}



