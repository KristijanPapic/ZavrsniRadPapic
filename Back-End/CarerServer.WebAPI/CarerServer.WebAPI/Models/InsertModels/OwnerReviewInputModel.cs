using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Model.DomainModels
{
    public class OwnerReviewInputModel
    {
        private Guid id;
        private Guid ownerId;
        private int overallRating;
        private int petRating;
        private int courtesyRating;
        private bool isPoster;

        public Guid Id { get => id; set => id = value; }
        public Guid OwnerId { get => ownerId; set => ownerId = value; }
        public int OverallRating { get => overallRating; set => overallRating = value; }
        public int PetRating { get => petRating; set => petRating = value; }
        public int CourtesyRating { get => courtesyRating; set => courtesyRating = value; }
        public bool IsPoster { get => isPoster; set => isPoster = value; }
    }
}
