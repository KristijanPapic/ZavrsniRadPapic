using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Model
{
    public class RatingsModel
    {
        private int count;
        private decimal overallRating;
        private decimal petRating;
        private decimal courtesyRating;

        public int Count { get => count; set => count = value; }
        public decimal OverallRating { get => overallRating; set => overallRating = value; }
        public decimal PetRating { get => petRating; set => petRating = value; }
        public decimal CourtesyRating { get => courtesyRating; set => courtesyRating = value; }
    }
}
