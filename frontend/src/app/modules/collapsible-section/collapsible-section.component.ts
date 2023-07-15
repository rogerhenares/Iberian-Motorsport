import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
    selector: 'app-collapsible-section',
    templateUrl: './collapsible-section.component.html'
})
export class CollapsibleSectionComponent {

    @Input() sectionTitle = '';
    @Input() isOpen = false;
    @Output() toggle = new EventEmitter<void>();

    onToggle() {
        this.toggle.emit();
    }
}
