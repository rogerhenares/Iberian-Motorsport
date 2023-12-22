import {Component, OnInit, ViewChild, ElementRef, AfterViewInit} from '@angular/core';
import {Router} from '@angular/router';
import {AppContext} from "../../util/AppContext";
import Swiper from 'swiper'
import { register } from 'swiper/element/bundle';

register();


@Component({
    selector: 'app-summary',
    templateUrl: './summary.component.html',
    styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {
    @ViewChild('imagenEspecial') imagenEspecial: ElementRef;

    constructor(
        public router: Router,
        public appContext : AppContext,
    ) {}

    ngAfterViewInit(): void {
        new Swiper('.swiper-container', {
            slidesPerView: 4, // Mostrar 4 cartas a la vez
            spaceBetween: 50, // Espacio entre las cartas
            navigation: {
              nextEl: '.swiper-button-next', // Botón para ir a la siguiente carta
              prevEl: '.swiper-button-prev', // Botón para ir a la carta anterior
            },
          });
    }
    

    ngOnInit(): void {
    }
}

