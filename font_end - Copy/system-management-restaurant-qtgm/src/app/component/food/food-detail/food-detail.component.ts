import {AfterContentInit, Component, OnInit} from '@angular/core';
import {render} from 'creditcardpayments/creditCardPayments';

@Component({
  selector: 'app-food-detail',
  templateUrl: './food-detail.component.html',
  styleUrls: ['./food-detail.component.css']
})
export class FoodDetailComponent implements OnInit, AfterContentInit {

  constructor() {
  }

  ngOnInit(): void {

  }

  ngAfterContentInit(): void {
    render(
      {
        id: '#payments',
        currency: 'VND',
        value: '100.00',
        onApprove: (details) => {
           alert('Thanh toán thành công');
        }
      }
    );
  }

}
