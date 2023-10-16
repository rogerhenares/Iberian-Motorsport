import { Component, Input, Output, EventEmitter } from '@angular/core';
import {animate, AUTO_STYLE, state, style, transition, trigger} from "@angular/animations";

const DEFAULT_DURATION = 200;

@Component({
    selector: 'app-collapsible-section',
    styleUrls: ['./collapsible-section.component.css'],
    templateUrl: './collapsible-section.component.html',
    animations: [
        trigger('collapse', [
            state('false', style({ height: '40px', visibility: AUTO_STYLE})),
            state('true', style({ height: AUTO_STYLE, visibility: 'visible'})),
            transition('false => true', animate(DEFAULT_DURATION + 'ms ease-in')),
            transition('true => false', animate(DEFAULT_DURATION + 'ms ease-out'))
        ])
    ]
})

export class CollapsibleSectionComponent {

    @Input() sectionTitle = '';
    @Input() isOpen = false;
    @Output() toggle = new EventEmitter<void>();

    onToggle() {
        this.toggle.emit();
    }
}
