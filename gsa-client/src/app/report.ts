import { TeamTransaction } from './teamTransaction';
import Big from 'big.js';

export class Report {

    withdrawnTransactions: Array<TeamTransaction>;
    quarter: string;
    year: string;
    teamLoss: Big;
    teamWithdrawalCost: Big;

    constructor() {
    }
}